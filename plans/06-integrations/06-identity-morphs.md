# 06.06 — First-Party Form Engine — Druid, Metamorph e assimilação primal

> **Status:** PLANO ARQUITETURAL / IMPLEMENTAÇÃO NÃO INICIADA.  
> **Baseline:** `main@594cbeea70cb3e16d698933f35391e05eddd7c60`.  
> **Authority física:** modlist 2026-09-08, 595 entradas, NeoForge 21.1.248.  
> **Mudança de escopo:** Identity/Identity2 e Woodwalkers não existem mais na modlist atual. Este plano substitui a antiga integração de morph por um engine first-party do RPG Skill Tree.

## 1. Objetivo

Criar uma transformação verdadeira, server-authoritative e data-driven para duas classes distintas:

- **Druid:** fauna/bestas/formas naturais + magia da natureza + companions, com **assimilação exclusiva de traços** das formas no corpo humano;
- **Metamorph:** especialista em transformação corporal ampla, podendo assumir formas naturais e também humanoides, monstros, undead e aberrant quando aprovados, porém **sem o sistema de assimilação primal humana do Druid**.

Transformar significa mais do que trocar skin/modelo. Quando uma capacidade estiver suportada de forma segura, a forma deve poder alterar características físicas e mecânicas coerentes com a entidade representada.

---

## 2. Authority e princípio de segurança

O jogador continua sendo `ServerPlayer`. O engine **não** transforma o jogador copiando cegamente uma instância inteira de entidade, NBT, AI ou capability stack externa.

A direção obrigatória é:

```text
EntityType / custom form ID
        ↓
FormClassificationCatalog
        ↓
FormDefinition
        ↓
capabilities reproduzíveis e auditadas
        ↓
FormRuntimeState do jogador
```

Cada propriedade suportada precisa ser declarada. O comportamento padrão para uma capacidade desconhecida é **não conceder**.

Provider-native first continua valendo: quando uma habilidade especial pertence a um mod externo, usar contrato/API/hook comprovado daquele provider; se não houver caminho seguro, o adapter fica fail-closed.

---

## 3. Famílias de forma

Cada forma recebe classificação explícita e data-driven. Não inferir a classe apenas por `instanceof Animal`, nome de registro ou aparência.

Famílias mínimas:

- `NATURAL_BEAST` — animais/fauna e criaturas bestiais naturais;
- `AQUATIC_NATURAL` — formas naturais com adaptação aquática;
- `AERIAL_NATURAL` — formas naturais capazes de voo/planagem real;
- `MYTHIC_BEAST` — criaturas bestiais/fantásticas aprovadas, por exemplo determinados dragões;
- `HUMANOID`;
- `MONSTER`;
- `UNDEAD`;
- `ABERRANT`;
- `TECHNICAL_DENY` — armor stands técnicas, markers, projeções, entities internas, bosses/veículos problemáticos etc.;
- `CUSTOM_FORM` — formas first-party desenhadas pelo projeto, como um futuro Owlbear.

A classificação pode receber tags adicionais como `dragon`, `clawed`, `winged`, `armored_hide`, `venomous`, `burrower`, `fire_breather` etc., mas tags só habilitam conteúdo quando um `FormDefinition`/adapter realmente declara a capacidade.

---

## 4. Permissões de classe

### 4.1 Druid

Pode desbloquear e assumir:

- `NATURAL_BEAST`;
- `AQUATIC_NATURAL`;
- `AERIAL_NATURAL`;
- `MYTHIC_BEAST` somente após gates avançados próprios;
- `CUSTOM_FORM` explicitamente marcada como druid-compatible.

Não recebe por padrão formas humanoides/undead/monstruosas apenas porque consegue derrotá-las.

### 4.2 Metamorph

Pode desbloquear e assumir:

- todas as famílias naturais autorizadas ao Druid;
- `HUMANOID`;
- `MONSTER`;
- `UNDEAD`;
- `ABERRANT`;
- `CUSTOM_FORM` marcada como metamorph-compatible.

Essa amplitude é a vantagem do Metamorph.

### 4.3 Diferença decisiva

A amplitude de forma do Metamorph **não** lhe dá `Primal Trait Imprinting`.

O Druid tem menos famílias, mas aprende a **internalizar aspectos das criaturas e manifestá-los sem transformação completa**.

---

## 5. Aquisição de formas

### 5.1 Gate temporal

Kills anteriores à obtenção da classe/gateway relevante **não** contam retroativamente.

Fluxo:

```text
classe/gateway ativo
      ↓
kill futura elegível e causalmente atribuída ao jogador
      ↓
classificação da entidade
      ↓
checagem de denylist/provider
      ↓
unlock idempotente da FormDefinition
```

### 5.2 Regras

- somente kill causalmente atribuída ao jogador conta;
- summon/companion/DoT precisam usar o pipeline causal já existente quando aplicável;
- a mesma forma não é concedida duas vezes;
- entidade técnica/blacklisted nunca desbloqueia;
- boss/mythic form pode exigir node/gateway adicional além da kill;
- creative/admin unlock, se existir, deve usar comando explícito e auditável, não compartilhar caminho de gameplay;
- reload de datapack não inventa unlock passado.

### 5.3 Discovery versus uso

É permitido separar:

- **forma descoberta/desbloqueada**;
- **forma equipável/selecionável**;
- **forma atualmente utilizável** diante dos gates presentes.

Isso permite preservar descoberta quando um provider temporariamente some sem fingir que a forma continua funcional.

---

## 6. FormDefinition

Uma definição de forma descreve somente características que o engine sabe reproduzir com segurança.

Campos conceituais:

- `form_id` estável;
- `source_entity_type` opcional;
- família/classificação;
- providers requeridos;
- modelo/renderer binding;
- dimensões/hitbox;
- eye height;
- escala visual aprovada;
- movimento terrestre;
- salto;
- swim/underwater movement;
- climb/crawl;
- flight/glide quando autorizado;
- base/max health da forma ou fórmula de scaling;
- armor/toughness/resistências;
- attack damage/attack speed/reach quando a forma combate fisicamente;
- fall behavior;
- breathing rules;
- imunidades/resistências específicas;
- active abilities declaradas;
- traits extraíveis pelo Druid;
- deny/technical flags;
- adapter/provider capability necessário.

O schema rejeita campo desconhecido, números inválidos e combinações inseguras antes de publicar o snapshot.

---

## 7. Vida e dano durante transformação

O projeto quer que a transformação tenha corpo e sobrevivência próprios. A direção adotada é **pool de vida da forma**, inspirado na fantasia clássica de Wild Shape, mas sem criar heal infinito.

### 7.1 Invariantes

- entrar numa forma não pode funcionar como cura gratuita repetível;
- sair/reentrar não pode resetar dano da forma indefinidamente;
- dano letal/reversão precisa ter semântica determinística;
- logout/death/dimension change preservam ou limpam o estado conforme regra explícita, nunca por acidente;
- porcentagem/quantidade remanescente da forma deve persistir quando a política de cooldown/duração permitir retomar a mesma transformação.

### 7.2 Modelo a prototipar

A implementação deve comparar duas opções em GameTests/simulação antes de congelar números:

1. pool de forma persistente por ativação, com overflow de dano para o jogador;
2. temporary vitality layer derivada do level/forma, sem alterar permanentemente o HP base.

O design final deve evitar full-heal cycling e ser compatível com autoleveling.

---

## 8. Druid — Primal Trait Imprinting

Cada forma natural pode expor **traços extraíveis** além da transformação completa.

Exemplos de fantasia que o sistema deve conseguir representar, sem prometer adapter antes de validá-lo:

- galinha/ave leve -> slow fall/controle de queda;
- felino -> garras/agilidade predatória;
- criatura de couro espesso -> defesa natural;
- criatura aquática -> respiração/movimento aquático;
- criatura alada -> asas/voo ou glide conforme tier;
- dragão -> escamas/resistência elemental;
- dragão avançado -> sopro elemental como manifestação ativa humana, somente após especialização/gate de alto nível.

### 8.1 Slots de traço

Traços não se acumulam todos permanentemente.

O Druid equipa um número limitado de **Primal Trait Slots**. Categorias sugeridas:

- Locomotion;
- Defense;
- Offense;
- Utility;
- Active Manifestation.

A capacidade e as combinações permitidas são data-driven e expandidas por perks/especializações.

### 8.2 Por que slots

Isso transforma a coleção de formas em uma biblioteca de builds, em vez de um checklist que entrega centenas de buffs passivos simultâneos.

### 8.3 Authority

O servidor resolve a composição final. O cliente apenas seleciona entre traits já desbloqueados e envia intenção.

---

## 9. Especializações Druid propostas para auditoria da Tree 3

Os nomes ainda passam pelo audit das especializações, mas o design exige pelo menos duas fantasias distintas.

### 9.1 Wild Shape / Combat Shapeshifter

Foco:

- duração;
- cooldown/recurso;
- HP/armor da forma;
- dano natural;
- size tiers;
- formas aquáticas/aéreas;
- formas míticas;
- cast/utility limitado em forma quando explicitamente permitido.

### 9.2 Herança Primal

Foco:

- slots adicionais;
- manifestar garras/pele/guelras/asas em corpo humano;
- melhorar eficiência de traits;
- combinar traits compatíveis;
- liberar manifestações ativas de alto tier;
- capstone capaz de usar uma habilidade signature de forma sem transformação completa, por exemplo sopro de uma forma dracônica realmente dominada.

`Beastmaster` não será usado como nome dessa especialização porque já é uma classe própria.

---

## 10. Metamorph — identidade mecânica

Metamorph aprofunda **adaptabilidade de corpo**, não herança primal.

Eixos possíveis da futura Tree 3:

- troca rápida de formas;
- retenção de duração/cooldown;
- eficiência de formas monstruosas;
- acesso a anatomias incomuns;
- capacidades específicas de families autorizadas;
- controle de tamanho/locomoção;
- formas undead/aberrant;
- custom monster forms first-party.

Uma habilidade especial de uma forma pode funcionar durante a transformação se o adapter declarar isso, mas não é convertida automaticamente em poder humano como ocorre com o Druid/Herança Primal.

---

## 11. Providers e criaturas atuais do pack

A modlist física atual contém fontes de fauna/monstros relevantes, incluindo **Alex's Mobs Continued**, **Alex's Caves Continued** e **Ice and Fire**. Elas aumentam o catálogo potencial, mas não autorizam classificação automática.

Para cada provider:

1. confirmar versão física;
2. inspecionar API/source da versão exata quando necessário;
3. mapear EntityTypes elegíveis;
4. identificar entities técnicas/bosses multipart/problemáticas;
5. verificar renderer/model compatibility;
6. verificar movimento e habilidades especiais;
7. criar adapter somente para capacidades comprováveis;
8. permanecer fail-closed para o resto.

Um dragão de Ice and Fire, por exemplo, não recebe automaticamente flight + breath + immunities só porque visualmente é um dragão. Cada capability precisa de binding real.

---

## 12. CustomFormDefinition

O engine deve suportar formas que não correspondem 1:1 a uma entidade externa.

Caso-alvo: **Owlbear** e outras formas próprias futuras.

`CUSTOM_FORM` usa os mesmos contracts de hitbox, attributes, movement, abilities, traits e deny rules. Isso evita criar um segundo engine quando o projeto começar a adicionar criaturas próprias.

---

## 13. Renderer e hitbox

Renderização e física precisam ser desacopladas.

- renderer pode usar modelo do provider somente quando legal/técnicamente permitido;
- dimensões/hitbox vêm da FormDefinition validada;
- camera/eye height acompanha o corpo com limites de segurança;
- não permitir forma gigante clipar permanentemente em blocos ao reverter;
- ativação/reversão deve verificar espaço seguro;
- se não houver posição segura, falhar fechado ou usar política de fallback previamente definida, nunca teletransportar arbitrariamente.

Client-only renderer nunca altera authority de movimento/combate.

---

## 14. Active abilities

Habilidades especiais são registradas por ID semântico e executadas por adapters/capabilities explícitos.

Exemplos conceituais:

- breath;
- teleport;
- explosion;
- venom;
- pounce;
- burrow;
- roar;
- aquatic dash.

Não existe `switch` genérico baseado no nome da entidade.

Cada habilidade declara:

- owner/provider;
- custo/recurso;
- cooldown;
- causalidade;
- alvo/raycast;
- dano/status;
- anti-spam;
- deduplicação;
- comportamento quando provider está ausente.

---

## 15. Persistência e sync

Persistir apenas estado canônico necessário:

- formas desbloqueadas;
- progressão/mastery da forma, se existir;
- traits desbloqueados;
- loadout de traits;
- forma ativa e estado mínimo necessário para recuperação segura;
- cooldowns/duração que precisam sobreviver relog;
- versão/schema para migração.

Não persistir cópia de uma entidade inteira.

O cliente recebe snapshot suficiente para UI/renderer, nunca authority para conceder forma/trait ou alterar stats.

---

## 16. Migração do contrato antigo

O runtime/documentação atual de Druid/Metamorph contém referências a Identity2. A implementação futura deve:

1. remover gates/optional integrations obsoletos de Identity/Identity2/Woodwalkers;
2. preservar IDs de classe Druid/Metamorph;
3. mapear nodes antigos para os novos conceitos quando houver equivalência real;
4. refund/deativar nodes sem equivalente;
5. manter save migration versionada;
6. atualizar tests/validators/textos que ainda afirmam que Identity2 é authority;
7. não reintroduzir dependência externa apenas para manter um teste antigo verde.

---

## 17. Fases de implementação

### Fase A — catálogo e classificação

- schemas de FormDefinition/Classificação;
- reload atômico;
- denylist;
- fixtures vanilla mínimas;
- tests de parse/classification.

### Fase B — runtime corporal mínimo

- estado de transformação;
- dimensões/camera;
- atributos básicos;
- ativar/reverter;
- death/relog/dimension;
- multiplayer sync.

### Fase C — locomotion e combate

- swim/climb/glide/flight por capability;
- ataques/armor/resistências;
- causalidade;
- anti-abuso.

### Fase D — aquisição por kill

- event/correlation correta;
- class gates;
- dedupe;
- no retroactive unlock.

### Fase E — Druid traits

- trait definitions;
- slots/loadouts;
- manifestations;
- requisitos de specialization.

### Fase F — adapters externos

- Alex's Mobs Continued;
- Alex's Caves Continued;
- Ice and Fire;
- demais providers só após audit.

### Fase G — custom forms

- pipeline para Owlbear/outros assets próprios;
- compatibilidade integral com o mesmo runtime.

---

## 18. Matriz de testes obrigatória

- unlock só após classe/gateway ativo;
- kill anterior não é retroativa;
- kill duplicada não duplica unlock;
- entidade denylisted não desbloqueia;
- Druid não recebe humanoid/monster indevido;
- Metamorph recebe natural + monster conforme catálogo;
- Metamorph não equipa Primal Trait;
- Druid equipa apenas slots permitidos;
- trait não persiste após remoção/reconfiguração inválida;
- flight/breath/etc. só existem com capability comprovada;
- provider ausente falha fechado;
- reverter forma não duplica HP/cura;
- death, respawn, relog e dimension change não corrompem estado;
- hitbox/camera restauram corretamente;
- dedicated server não carrega renderer/client classes;
- dois jogadores transformados não contaminam estado um do outro;
- save antigo com nodes Identity2 migra/refunda determinísticamente;
- autoleveling e mob scaling não criam overflow/NaN em fórmulas de forma.

---

## 19. Acceptance

O Form Engine só está pronto quando Druid e Metamorph não dependem de Identity2/Woodwalkers, formas são capacidades reproduzíveis e auditáveis em vez de cópias de Entity/NBT, Druid possui transformação + assimilação primal controlada por slots, Metamorph possui catálogo de corpos mais amplo sem roubar a identidade do Druid, e qualquer provider não suportado falha fechado sem corromper a progressão.