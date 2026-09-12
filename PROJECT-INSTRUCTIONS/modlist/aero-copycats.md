# aero_copycats

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b88eafcc4c338a0b9a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** aero_copycats
- **Arquivo JAR:** `aerocopycats-1.1.1.jar`
- **Versão 1.21.1:** `1.1.0`
- **Estado no pack:** Integrado ao Github
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #13: `aerocopycats-1.1.1.jar` conferido com metadata/runtime `1.1.0`; divergência filename↔runtime preservada por evidência, sem normalização; corpo técnico, decisão e estado preservados.
- **Categoria:** Compat
- **Compatibilidade/Riscos:** Pode alterar massa total, centro de massa e estabilidade de ships. Updates de Copycats+ podem introduzir formas ainda não cobertas; validar catálogo atual, montagem/desmontagem e persistência.
- **Decisão:** Sem decisão
- **Dependências:** Required upstream: Create Aeronautics + Create: Copycats+. Pack atual: Aeronautics 1.3.2 (bundle) e Copycats+ 3.0.9+mc.1.21.1-neoforge presentes. Sable é a infraestrutura física subjacente.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/copycats-aeronautics-weight
- **Função:** Define massa física (kpg) coerente para blocos do Create: Copycats+ em Create Aeronautics/Sable, incluindo escala por número de camadas em Copycat Layers.
- **Histórico da decisão:**
- **Observações:** Não normalizar versão: JAR `aerocopycats-1.1.1.jar`, runtime `1.1.0`. Não adiciona Copycats nem lift por si só.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Copycats+ Aeronautics Weight e fontes já auditadas no dossiê. Reconciliação final: JAR permanece `aerocopycats-1.1.1.jar` e metadata/runtime permanece `1.1.0`; divergência intencional preservada, sem erro físico.
- **Sobreposição:** Complementar a Create Aeronautics: Copycat Wing 1.0.3: Aero Copycats define massa; Copycat Wing define reconhecimento aerodinâmico/lift. Não são duplicatas.
- **Data da última decisão:**

## Escopo e papel
Addon de física para **Create: Copycats+** dentro de **Create Aeronautics/Sable**. Sua responsabilidade é atribuir massa (`kpg`) coerente às formas Copycat, evitando que geometrias diferentes sejam tratadas com peso genérico. Em `Copycat Layers`, a massa escala conforme a quantidade de camadas.

## Runtime e autoridade
- JAR físico: `aerocopycats-1.1.1.jar`.
- Mod ID: `aerocopycats`.
- Nome runtime: `aero_copycats`.
- Runtime metadata: **1.1.0**.
- Arquivo/release publicada: **1.1.1**.
A divergência filename/publicação ↔ metadata runtime é preservada; não deve ser normalizada sem evidência do JAR.

## Dependências
- **Required no CurseForge:** Create Aeronautics e Create: Copycats+.
- Pack atual: Copycats+ `3.0.9+mc.1.21.1-neoforge` e Aeronautics `1.3.2` estão presentes.
- Sable é o solver físico sobre o qual a massa produz efeito, mas a página de relações do projeto publica Aeronautics + Copycats+ como dependências requeridas.

## Integrações no pack
Existe também `Create Aeronautics: Copycat Wing 1.0.3`. Os dois addons são complementares: **Aero Copycats define massa**, enquanto **Copycat Wing registra Copycats como superfícies aerodinâmicas/lift**. Uma peça pode portanto participar simultaneamente de massa e aerodinâmica sem duplicação lógica.

## Compatibilidade, sobreposição e riscos
O ponto sensível é balanceamento físico: alterações nas massas podem mudar centro de massa, estabilidade, inércia e comportamento de veículos já construídos. Atualizações de Copycats+ que adicionem novas formas precisam ser testadas para cobertura. Não há evidência de um segundo top-level no pack exercendo a mesma função de massa para todo o catálogo Copycats+.

## Limites
Não cria blocos Copycat, não muda textura/camuflagem e não transforma uma forma em wing por si só. Também não é um controlador de voo.

## Testes recomendados
1. Comparar massa total do ship com/sem diferentes formas Copycat.
2. Testar Copycat Layers com 1, múltiplas e máxima quantidade de camadas.
3. Combinar com Copycat Wing e validar que massa e lift coexistem.
4. Montar/desmontar e salvar/recarregar ships sem perda de propriedades.
5. Testar formas novas adicionadas pelo Copycats+ atual para detectar lacunas de cobertura.

## Evidências
- [CurseForge oficial — Copycats+ aeronautics weight](https://www.curseforge.com/minecraft/mc-mods/copycats-aeronautics-weight)
- Modlist física atual e guia consolidado de Tecnologia.