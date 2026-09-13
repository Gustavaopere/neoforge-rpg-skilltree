# SnackPirate's Aeromancy Additions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c0937cf0aa06275635
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `aero_additions-1.2.8.jar`, mod id `aero_additions`, runtime `1.2.8`, mixin `aeromancy.mixins.json`; ExpandAbility `12.0.0` confirmado como JAR aninhado em `META-INF/jarjar/`; Iron's Spells `3.16.3` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**, onde Aeromancy 1.2.8, seu ExpandAbility 12.0.0 aninhado e Iron's 3.16.3 estão confirmados.
- A própria página mantém uma divergência upstream relevante: a descrição atual cita **Feather Fall**, enquanto o changelog de 1.2.3 registra rework para **Feather Flight**. Esta exportação não normaliza o nome por suposição; registry/display runtime 1.2.8 deve decidir.

## Propriedades do banco

- **Mod:** SnackPirate's Aeromancy Additions
- **Arquivo JAR:** `aero_additions-1.2.8.jar`
- **Versão 1.21.1:** 1.2.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia
- **Função:** Addon de Iron's Spells que adiciona escola Wind/Aeromancy focada em defesa, agilidade, controle vertical e mobilidade; inclui Windmaker Armor, Air Staff, Updraft Tome e spells como Wind Charge, Airstep, Updraft, Asphyxiate e Wind Shield, além da linha Feather Fall/Feather Flight.
- **Dependências:** Required upstream no CurseForge: Iron's Spells 'n Spellbooks. O JAR físico 1.2.8 embute `expandability-neoforge-12.0.0.jar` via jarjar. Em changelog anterior, ExpandAbility foi descrito como required para Feather Flight; na build física atual ele está empacotado internamente e não deve ser contado como mod top-level separado.
- **Sobreposição:** Sobreposição temática/mecânica com outras escolas de ar/vento e perks de mobilidade, mas não implica registry collision. Wind Shield toca projectile reflection; Updraft/Asphyxiate tocam CC. Feather Flight/linha de queda toca voo/gravidade e deve ser testada com Epic Fight, Pehkui, gliders e outros providers de flight.
- **Compatibilidade/Riscos:** Toca spell registry, escola, movimento aéreo, gravidade/queda, projéteis e crowd control. Principal risco no pack é stacking com outros sistemas de voo/mobilidade/slow falling e escolas de vento. Há discrepância documental: a descrição atual ainda cita Feather Fall, enquanto changelog 1.2.3 registra rework para Feather Flight; confirmar registry/display atual em runtime antes de criar perks por nome.
- **Observações:** Dossiê aprofundado concluído em 07/09/2026. Fonte pública exata validada. ExpandAbility 12.0.0 confirmado como jarjar físico. Divergência Feather Fall vs Feather Flight registrada como ponto de runtime verification, não reconciliada por suposição.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial SnackPirate's Aeromancy Additions 1.2.8 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `aero_additions-1.2.8.jar` / `1.2.8`; ExpandAbility continua jar-in-jar, não top-level; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/snackpirates-aeromancy-additions
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #9: `aero_additions-1.2.8.jar` / `1.2.8` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

## Dossiê técnico de compatibilidade

### 1. Identidade auditada
- **Tipo:** addon de magia para Iron's Spells.
- **JAR:** `aero_additions-1.2.8.jar`.
- **Runtime:** `1.2.8`.
- **Jarjar interno:** `expandability-neoforge-12.0.0.jar`.
- **Ambiente upstream:** cliente e servidor.

### 2. Descrição técnica detalhada
SnackPirate's Aeromancy Additions adiciona uma escola Wind/Aeromancy ao ecossistema de Iron's Spells. O design é orientado a defesa, agilidade, manipulação vertical e posicionamento, usando Breeze Rod como focus material da escola.

O conteúdo público inclui Windmaker Armor, Air Staff, Updraft Tome e spells documentados como Wind Charge, Airstep, Updraft, Asphyxiate e Wind Shield. A documentação pública apresenta uma inconsistência relevante: a descrição atual ainda chama uma magia de **Feather Fall**, enquanto o changelog de 1.2.3 afirma que Feather Fall foi refeito como **Feather Flight**, com novo efeito de voo e dependência ExpandAbility. Como a build física é 1.2.8, a auditoria não normaliza esse nome por suposição; o registry/runtime deve decidir o nome/ID efetivo.

### 3. Superfícies tocadas
- spell registry de Iron's;
- escola de magia Wind;
- spell power/equipment da escola;
- movimento aéreo e saltos em sequência;
- gravidade/queda/flight effect;
- knock-up e fall-damage setup;
- projectile reflection/defesa;
- debuff respiratório/slow;
- loot/progressão por Updraft Tome e Trial Chambers.

### 4. Dependências
**Required upstream**
- Iron's Spells 'n Spellbooks.

**Componente interno confirmado pela modlist física**
- ExpandAbility `12.0.0` empacotado via jarjar dentro de `aero_additions-1.2.8.jar`.

O changelog 1.2.3 chamou ExpandAbility de nova required dependency para Feather Flight. Na build 1.2.8 do pack, o artefato está incorporado; portanto ele não é uma entrada top-level separada.

### 5. Incompatibilidades declaradas upstream
Nenhuma relation `Incompatible` foi publicada na página CurseForge consultada.

### 6. Sobreposição e riscos no pack
**Outras escolas de vento/ar:** a sobreposição pode ocorrer em fantasia, dano/CC e mobilidade, mas escolas com namespaces diferentes podem coexistir. Antes de deduplicar, comparar registry ID, focus item, scaling e papel de cada spell.

**Voo/mobilidade:** a linha Feather Flight/queda deve ser testada com gliders, voo de equipment, perks de movimento, Pehkui e Epic Fight. O risco é stacking de movimento/gravity, não incompatibilidade comprovada.

**Wind Shield:** reflection de projéteis pode interagir com projéteis físicos e mágicos de vários providers. Validar ownership/damage source e evitar loops de reflexão.

**Updraft:** launch + stun/fall damage pode cruzar sistemas de poise, knockback resistance e Epic Fight. A autoridade do efeito deve continuar no spell nativo, e perks devem observar/alterar atributos aprovados sem reimplementar o CC.

### 7. Matriz mínima de validação
1. registrar escola Wind sem duplicate IDs;
2. lançar cada spell da build 1.2.8 e capturar registry IDs reais;
3. confirmar se Feather Fall ou Feather Flight é o nome/runtime efetivo;
4. testar Airstep/flight com Epic Fight e Pehkui;
5. testar Wind Shield contra projéteis vanilla, Iron's e addons;
6. testar Updraft em mobs com knockback resistance/world scaling;
7. validar Asphyxiate em alvos imunes/sem respiração quando aplicável;
8. validar gear da escola e spell power;
9. dedicated-server smoke;
10. confirmar que ExpandAbility interno carrega sem top-level duplicado.

### 8. O que não faz
- não cria um segundo sistema de mana independente do Iron's;
- não torna toda magia de vento do pack equivalente;
- não prova incompatibilidade com gliders/Epic Fight/Pehkui;
- ExpandAbility jarjar não é um mod top-level adicional.

### 9. Evidência
- **Modlist física:** Aeromancy 1.2.8 + ExpandAbility 12.0.0 jarjar.
- **Upstream:** Iron's required; escola Wind, equipment e spells; changelog de Feather Flight/ExpandAbility.
- **Guia de magia:** papel da escola preservado.
- **Incerteza explícita:** Feather Fall vs Feather Flight precisa de confirmação do runtime 1.2.8.
