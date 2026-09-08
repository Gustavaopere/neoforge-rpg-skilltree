# Visual Style Bible — RPG Skill Tree / Modpack

Status: **canônico para assets próprios; contextual para conteúdo de terceiros**  
Alvo técnico: Minecraft 1.21.1 · NeoForge 21.1.x · Java 21  
Autoridade de versão/presença: `PROJECT-INSTRUCTIONS/skills/VERSION-AUTHORITY.md`

## 1. Objetivo

Esta Bíblia evita que assets próprios tecnicamente corretos pareçam pertencer a jogos diferentes. Ela define uma gramática visual para modelos, texturas, animações, VFX, áudio visualizado em UI e presentation feedback sem transformar resource packs, shaders ou VFX client-side em autoridade de gameplay.

Ela **não autoriza copiar** texturas, modelos, animações, sons ou efeitos de mods/resource packs de terceiros. Eles são referências de compatibilidade e linguagem, sujeitos a licença/proveniência.

## 2. Contexto visual real do pack

Snapshot editorial conferido em 2026-09-08 no banco `Auditoria Mestre da Modlist — NeoForge 1.21.1`:

- **Excalibur** é a base visual atual e substitui Whimscape;
- **Fresh Animations 1.10.4**, Fresh Animations: Extensions `1.8.1`, Player Extension `1.1` e patch Excalibur/Fresh Animations estão ativos;
- família **Mobs Refreshed** e integrações com Fresh Animations estão ativas;
- **Mandala's GUI — Dark mode** e add-ons/compatibilidades compõem a linguagem principal de UI;
- **Complementary Shaders — Reimagined r5.9** é o shader ativo catalogado;
- o pack também contém EMF/ETF/ESF, Player Animator e outras camadas de apresentação.

A modlist física continua autoridade para JARs e versões. Resource packs/shaders catalogados no Notion são contexto visual; seus binários não foram promovidos a fonte de especificação técnica automática.

## 3. Princípios visuais obrigatórios

### 3.1 Minecraft-readable first

- Silhueta deve continuar legível em distância real de gameplay.
- Geometria deve preservar leitura voxel/blockbench; detalhe não justifica microgeometria sem função visual.
- Proporção estilizada é preferível a realismo fotográfico quando o realismo quebra a linguagem do pack.
- Forma primária > forma secundária > detalhe. Se a forma primária não funciona, textura/glow não deve mascarar o problema.

### 3.2 Estilização medieval/fantasia como base

A camada project-owned deve conversar com a base Excalibur sem imitá-la pixel a pixel. O eixo é fantasia medieval estilizada, materiais legíveis e ornamento controlado. Sistemas tecnológicos podem puxar para linguagem industrial; domínios mágicos podem romper a base por contraste deliberado, nunca por inconsistência acidental.

### 3.3 Material antes de emissive

- Metal deve ler como metal por valor, bordas, desgaste e contraste, não apenas por brilho.
- Pedra deve preservar massa/porosidade sem ruído fotográfico fino.
- Madeira deve ter direção/fibra estilizada coerente com escala.
- Tecido deve comunicar dobra/estrutura em poucos clusters, evitando noise uniforme.
- Emissive/glow é sinal semântico e focal; não substitui definição do material base.

### 3.4 Shader enhancement, not dependency

Assets devem permanecer compreensíveis sem depender de bloom, volumetrics ou exposição do shader. Complementary Reimagined pode melhorar a cena, mas não deve ser a única razão pela qual um símbolo, hitbox visual, contraste de item ou telegraph é legível.

## 4. Textura, resolução e texel density

Não existe resolução global fixa inventada para todo asset.

Antes de escolher resolução:

1. selecionar o asset canônico mais próximo por tamanho/função;
2. medir a relação pixel ↔ unidade Minecraft em faces comparáveis;
3. registrar resolução e densidade escolhidas no `MODEL-ASSET-CONTRACT.md`/template correspondente;
4. manter densidade consistente entre partes visualmente equivalentes;
5. justificar exceção quando interface, emissive, boss ou item de proximidade exigir mais detalhe.

**Pendência deliberada:** os resource packs ativos não estão versionados neste repositório como fontes binárias auditáveis; portanto esta Bíblia não congela um número universal de pixels por bloco. Um Golden Sample pode escolher uma resolução local como exemplo, mas isso não vira regra global.

## 5. Paleta e contraste

- Paleta deve ter hierarquia: base dominante, material secundário, detalhe focal e emissive/acento quando aplicável.
- Estados de gameplay importantes não podem depender apenas de uma única cor.
- Saturação máxima deve ser reservada a foco, perigo, magia ativa ou feedback equivalente.
- Silhueta e valor devem continuar distinguíveis em iluminação clara e escura.

## 6. Linguagem por domínio

### 6.1 Fantasia/base neutra

Pedra, madeira, ferro/aço, couro e tecido estilizados. Ornamento deve reforçar função/identidade; evitar filigrana homogênea em toda superfície.

### 6.2 Arcano / magia geral

Geometria limpa + símbolos/runes localizados + energia com direção. O efeito deve comunicar origem, trajetória e resolução. Evitar “nuvem de partículas” sem forma ou timing.

### 6.3 Black Arcana

Black Arcana Corruption é identidade própria. Visual pode usar deformação, fissuras, marcas, energia anômala e contraste de material, mas **não deve ser confundido automaticamente com Enshrouded Shroud**. Qualquer ponte visual precisa ser deliberada e documentada.

### 6.4 Enshrouded Shroud

Shroud/Exposure pertence ao domínio Enshrouded. Linguagem deve comunicar campo/névoa/exposição e corrupção ecológica sem assumir que isso é Arcane Corruption. Blue/cyan ou outra assinatura só deve ser congelada quando o design do sistema específico aprovar.

### 6.5 Volcanoes

Calor, magma, cinza, pressão e toxicidade têm leitura ambiental/material. Evitar tratar tudo como “magia vermelha”. Magma deve ter massa/temperatura; cinza deve ter deposição/atmosfera; hazard telegraph deve continuar legível sob VFX intenso.

### 6.6 Tecnologia / Create

Peças project-owned de magitech/tecnologia devem respeitar leitura mecânica: estrutura, eixo, conexão, função e manutenção visual. Brass/copper/iron podem ser referência de linguagem industrial, mas não copiar texturas Create. Forma deve explicar função antes do ornament.

## 7. Modelos e silhueta

Todo modelo final deve registrar:

- função e distância típica de visualização;
- tamanho em unidades Minecraft;
- silhouette statement em uma frase;
- shape language (angular, arredondada, pesada, delgada etc.);
- hierarquia/bones e attachment points;
- bounds e áreas de interação visual;
- vistas frontal, lateral, traseira, três-quartos e escala in-game.

Modelos de item também devem ser verificados em GUI, ground, first-person e third-person quando esses contexts existirem.

## 8. Animação

O pack contém Fresh Animations, Player Animator, Epic Fight e compatibilidades de spellcasting; portanto animação project-owned deve assumir coexistência, não isolamento.

- Idle deve ter vida sem disputar atenção com gameplay.
- Attack/cast precisa de anticipation legível antes do commit quando a mecânica permitir.
- Impact visual deve alinhar com o boundary real de gameplay.
- Loops precisam de ciclo limpo e owner claro.
- Evitar clipping crítico com corpo/equipamento e conflito óbvio com first-person/third-person.
- A animação não pode criar uma segunda autoridade de hit/cast.

## 9. VFX

Lifecycle canônico geral:

`ANTICIPATION -> CHARGE -> RELEASE -> TRAVEL/ACTIVE -> IMPACT -> LINGER -> DECAY`

Nem todo efeito precisa de todas as fases. Omissão deve ser deliberada.

Backend order:

1. provider-native;
2. vanilla/NeoForge para efeito simples;
3. Photon para authored VFX avançado quando justificado;
4. AAA Particles/Effekseer quando o efeito Effekseer for deliberadamente escolhido e API/asset exatos estiverem comprovados.

Não empilhar Photon + AAA para o mesmo evento por padrão. Não usar particle count como métrica de qualidade.

## 10. UI e ícones

Mandala dark UI é contexto atual, então:

- ícones precisam sobreviver a fundo escuro e a estados desabilitados/cooldown;
- borda/shape não deve depender apenas de cor;
- símbolos devem ser legíveis em tamanho real da HUD/inventário;
- um mockup bonito em resolução ampliada não substitui QA no jogo.

## 11. Proveniência

Para cada asset project-owned, registrar:

- autor/origem;
- ferramenta/processo;
- licença ou status original;
- referências usadas;
- alterações derivativas quando houver base licenciada;
- restrições de redistribuição/atribuição.

Imagem/áudio gerado por IA não é automaticamente rights-clear; registrar termos/proveniência aplicáveis à criação.

## 12. Gate de aprovação

Nenhum asset é “final” apenas porque compila/exporta. Aprovação exige:

- contrato preenchido;
- structural QA quando aplicável;
- visual QA no Minecraft real;
- contexts de câmera relevantes;
- iluminação clara/escura;
- animação/impact timing quando aplicável;
- multiplayer/performance/lifecycle para VFX persistente;
- pendências explícitas quando alguma evidência não puder ser produzida.

Use `PROJECT-INSTRUCTIONS/skills/library/minecraft-visual-qa/SKILL.md` e os standards especializados.
